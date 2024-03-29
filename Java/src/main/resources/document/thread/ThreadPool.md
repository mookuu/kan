## 线程池

#### 线程池执行流程

![ThreadPoolFlow.png](images/ThreadPoolFlow.png)

1. 判断核心线程池是否已满，即已创建线程数是否小于corePoolSize？没满则创建一个新的工作线程来执行任务。已满则进入下个流程。
2. 判断工作队列是否已满？没满则将新提交的任务添加在工作队列，等待执行。已满则进入下个流程。
3. 判断整个线程池是否已满，即已创建线程数是否小于maximumPoolSize？没满则创建一个新的工作线程来执行任务，已满则交给饱和策略来处理这个任务。

#### 线程池创建要求

```java
public ThreadPoolExecutor(int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue){
        this(corePoolSize,maximumPoolSize,keepAliveTime,unit,workQueue,
        Executors.defaultThreadFactory(),defaultHandler);
        }
```

创建线程池，在构造一个新的线程池时，必须满足下面的条件：

1. corePoolSize（线程池基本大小）必须大于或等于0；
   
2. maximumPoolSize（线程池最大大小）必须大于或等于1；
   
3. maximumPoolSize必须大于或等于corePoolSize；
   
4. keepAliveTime（线程存活保持时间）必须大于或等于0；
   
5. workQueue（任务队列）不能为空；
   
6. threadFactory（线程工厂）不能为空，默认为DefaultThreadFactory类
   
7. handler（线程饱和策略）不能为空，默认策略为ThreadPoolExecutor.AbortPolicy。

#### 线程池参数

```java
public ThreadPoolExecutor(int corePoolSize,
        int maximumPoolSize,
        long keepAliveTime,
        TimeUnit unit,
        BlockingQueue<Runnable> workQueue,
        ThreadFactory threadFactory,
        RejectedExecutionHandler handler){
        if(corePoolSize< 0||
            maximumPoolSize<=0||
            maximumPoolSize<corePoolSize ||
            keepAliveTime< 0)
            throw new IllegalArgumentException();
        if(workQueue==null||threadFactory==null||handler==null)
            throw new NullPointerException();
        this.acc=System.getSecurityManager()==null?null:AccessController.getContext();
        this.corePoolSize=corePoolSize;
        this.maximumPoolSize=maximumPoolSize;
        this.workQueue=workQueue;
        this.keepAliveTime=unit.toNanos(keepAliveTime);
        this.threadFactory=threadFactory;
        this.handler=handler;
     }
```

* corePoolSize（线程池基本大小/核心线程池大小）
  
    当向线程池提交一个任务时，若线程池已创建的线程数小于corePoolSize，即便此时存在空闲线程，也会通过创建一个新线程来执行该任务，直到已创建的线程数大于或等于corePoolSize时，才会根据是否存在空闲线程，来决定是否需要创建新的线程。除了利用提交新任务来创建和启动线程（按需构造），也可以通过 prestartCoreThread() 或 prestartAllCoreThreads() 方法来提前启动线程池中的基本线程。 

* maximumPoolSize（线程池最大大小）
  
    线程池所允许的最大线程个数。当队列满了，且已创建的线程数小于maximumPoolSize，则线程池会创建新的线程来执行任务。另外，对于无界队列，可忽略该参数。
  
* keepAliveTime（线程存活保持时间）
  
    默认情况下，当线程池的线程个数多于corePoolSize时，线程的空闲时间超过keepAliveTime则会终止。但只要keepAliveTime大于0，allowCoreThreadTimeOut(boolean) 方法也可将此超时策略应用于核心线程。另外，也可以使用setKeepAliveTime()动态地更改参数。
  
* unit（存活时间的单位）
  
    时间单位，从细到粗顺序：NANOSECONDS（纳秒），MICROSECONDS（微妙），MILLISECONDS（毫秒），SECONDS（秒），MINUTES（分），HOURS（小时），DAYS（天）；
  
* workQueue（任务队列）
  
    用于传输和保存等待执行任务的阻塞队列。可以使用此队列与线程池进行交互：
  
    1. 如果运行的线程数少于 corePoolSize，则 Executor 始终首选创建新的线程，而不进行排队。
  
    2. 如果运行的线程数等于或多于 corePoolSize，则 Executor 始终首选将请求加入队列，而不创建新的线程。

    3. 如果无法将请求加入队列，则创建新的线程，除非创建此线程超出 maximumPoolSize，在这种情况下，任务将被拒绝。
  
* threadFactory（线程工厂）
  
    用于创建新线程。
  
    由同一个threadFactory创建的线程，属于同一个ThreadGroup，创建的线程优先级都为Thread.NORM_PRIORITY，以及是非守护进程状态。
  
    threadFactory创建的线程也是采用new Thread()方式，threadFactory创建的线程名都具有统一的风格：pool-m-thread-n（m为线程池的编号，n为线程池内的线程编号）
  
* handler（线程饱和策略）
  
    当线程池和队列都满了，则表明该线程池已达饱和状态。
  
    * ThreadPoolExecutor.AbortPolicy：处理程序遭到拒绝，则直接抛出运行时异常 RejectedExecutionException。(默认策略)
      
    * ThreadPoolExecutor.CallerRunsPolicy：调用者所在线程来运行该任务，此策略提供简单的反馈控制机制，能够减缓新任务的提交速度。
      
    * ThreadPoolExecutor.DiscardPolicy：无法执行的任务将被删除，不会抛出异常。
      
    * ThreadPoolExecutor.DiscardOldestPolicy：如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重新尝试执行任务（如果再次失败，则重复此过程）。不会抛出异常

### 队列

![BlockAndNoBlocking.png](images/BlockAndNoBlocking.png)
![BlockingQueue.png](images/BlockingQueue.png)

#### 分类

##### 直接提交队列
   
工作队列的默认选项是 SynchronousQueue，它将任务直接提交给线程而不保持它们。

在此，如果不存在可用于立即运行任务的线程，则试图把任务加入队列将失败，因此会构造一个新的线程(核心线程满->队列提交失败->线程池上限)。

此策略可以避免在处理可能具有内部依赖性的请求集时出现锁。直接提交通常要求无界 maximumPoolSizes 以避免拒绝新提交的任务。

当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。

##### 无界队列
   
使用无界队列（例如，不具有预定义容量的 LinkedBlockingQueue）将导致在所有 corePoolSize 线程都忙时新任务在队列中等待。

这样，创建的线程就不会超过 corePoolSize。（因此，maximumPoolSize 的值也就无效了）。

当每个任务完全独立于其他任务，即任务执行互不影响时，适合于使用无界队列；例如，在 Web 页服务器中。

这种排队可用于处理瞬态突发请求，当命令以超过队列所能处理的平均数连续到达时，此策略允许无界线程具有增长的可能性。
   
##### 有界队列
   
当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。

队列大小和最大池大小可能需要相互折衷

* 使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。

* 使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。

#### 工作队列BlockingQueue

BlockingQueue的插入/移除/检查这些方法，对于不能立即满足但可能在将来某一时刻可以满足的操作，共有4种不同的处理方式：第一种是抛出一个异常，第二种是返回一个特殊值（null 或 false，具体取决于操作），第三种是在操作可以成功前，无限期地阻塞当前线程，第四种是在放弃前只在给定的最大时间限制内阻塞。如下表格：

操作|抛出异常|特殊值|阻塞等待|阻塞超时等待
---|---|---|---|---
插入|add(e)|offer(e)	|put(e)	|offer(e, time, unit)
移除	|remove()|	poll()	|take()	|poll(time, unit)
检查	|element()|	peek()	|不可用	|不可用

#### 实现BlockingQueue接口的类

##### SynchronousQueue

>同步的阻塞队列。
> 
>进去一个元素，必须等待取出这个元素后才能放下一个元素。
> 
>即每个插入操作必须等待另一个线程的对应移除操作，等待过程一直处于阻塞状态。 同理，每一个移除操作必须等到另一个线程的对应插入操作。

>SynchronousQueue没有任何容量。不能在同步队列上进行 peek，因为仅在试图要移除元素时，该元素才存在；除非另一个线程试图移除某个元素，否则也不能（使用任何方法）插入元素；也不能迭代队列，因为其中没有元素可用于迭代。

>Executors.newCachedThreadPool使用了该队列。
> 
> 元素添加删除：put/take

##### LinkedBlockingQueue

基于链表的无界阻塞队列。

与ArrayBlockingQueue一样采用FIFO原则对元素进行排序。基于链表的队列吞吐量通常要高于基于数组的队列。

##### ArrayBlockingQueue
  
基于数组的有界阻塞队列。

队列按FIFO原则对元素进行排序，队列头部是在队列中存活时间最长的元素，队尾则是存在时间最短的元素。

新元素插入到队列的尾部，队列获取操作则是从队列头部开始获得元素。

这是一个典型的“有界缓存区”，固定大小的数组在其中保持生产者插入的元素和使用者提取的元素。

一旦创建了这样的缓存区，就不能再增加其容量。试图向已满队列中放入元素会导致操作受阻塞；试图从空队列中提取元素将导致类似阻塞。

ArrayBlockingQueue构造方法可通过设置fairness参数来选择是否采用公平策略，公平性通常会降低吞吐量，但也减少了可变性和避免了“不平衡性”，可根据情况来决策。

##### PriorityBlockingQueue

基于优先级的无界阻塞队列。

优先级队列的元素按照其自然顺序进行排序，或者根据构造队列时提供的 Comparator 进行排序，具体取决于所使用的构造方法。

优先级队列不允许使用 null 元素。依靠自然顺序的优先级队列还不允许插入不可比较的对象（这样做可能导致 ClassCastException）。虽然此队列逻辑上是无界的，但是资源被耗尽时试图执行 add 操作也将失败（导致 OutOfMemoryError）。

### 线程池关闭

调用线程池的shutdown()或shutdownNow()方法来关闭线程池

* shutdown原理
  
    将线程池状态设置成SHUTDOWN状态，然后中断所有没有正在执行任务的线程。
  
* shutdownNow原理
  
    将线程池的状态设置成STOP状态，然后中断所有任务(包括正在执行的)的线程，并返回等待执行任务的列表。

中断采用interrupt方法，所以无法响应中断的任务可能永远无法终止。

但调用上述的两个关闭之一，isShutdown()方法返回值为true。

当所有任务都已关闭，表示线程池关闭完成，则isTerminated()方法返回值为true。

当需要立刻中断所有的线程，不一定需要执行完任务，可直接调用shutdownNow()方法。

### 合理配置线程池

需要针对具体情况而具体处理，不同的任务类别应采用不同规模的线程池，任务类别可划分为CPU密集型任务、IO密集型任务和混合型任务。

#### 对于CPU密集型任务

线程池中线程个数(最大线程数)应尽量少，不应大于CPU核心数(一般几核就设几)

  * CPU密集型也叫计算密集型，指的是系统的硬盘、内存性能相对CPU要好很多，此时，系统运作大部分的状况是CPU Loading 100%，CPU要读/写I/O(硬盘/内存)，I/O在很短的时间就可以完成，而CPU还有许多运算要处理，CPU Loading很高。

#### 对于IO密集型任务

由于IO操作速度远低于CPU速度，那么在运行这类任务时，CPU绝大多数时间处于空闲状态，那么线程池可以配置尽量多些的线程，以提高CPU利用率

一般判断程序中十分消耗IO的线程数量，一般最大线程数设置成这个数的2倍

  * IO密集型指的是系统的CPU性能相对硬盘、内存要好很多，此时，系统运作，大部分的状况是CPU在等I/O (硬盘/内存) 的读/写操作，此时CPU Loading并不高。

#### 对于混合型任务

可以拆分为CPU密集型任务和IO密集型任务，当这两类任务执行时间相差无几时，通过拆分再执行的吞吐率高于串行执行的吞吐率，但若这两类任务执行时间有数据级的差距，那么没有拆分的意义。

### 线程池监控

利用线程池提供的参数进行监控，参数如下

* taskCount：线程池需要执行的任务数量。
  
* completedTaskCount：线程池在运行过程中已完成的任务数量，小于或等于taskCount。
  
* largestPoolSize：线程池曾经创建过的最大线程数量，通过这个数据可以知道线程池是否满过。如等于线程池的最大大小，则表示线程池曾经满了。
  
* getPoolSize：线程池的线程数量。如果线程池不销毁的话，池里的线程不会自动销毁，所以这个大小只增不减。
  
* getActiveCount：获取活动的线程数。

通过扩展线程池进行监控：继承线程池并重写线程池的beforeExecute()，afterExecute()和terminated()方法，可以在任务执行前、后和线程池关闭前自定义行为。如监控任务的平均执行时间，最大执行时间和最小执行时间等。

package logic.algorithms.list;

/**
 * @ClassName MergeList
 * @Description
 * @Author moku
 * @Date 2023/2/16 22:34
 * @Version 1.0
 */
public class MergeList {

    public ListNode merge(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }

        if (list2 == null) {
            return list1;
        }

        if (list1.val <= list2.val) {
            list1.next = merge(list1.next, list2);
            return list1;
        } else {
            list2.next = merge(list1, list2.next);
            return list2;
        }
    }
}

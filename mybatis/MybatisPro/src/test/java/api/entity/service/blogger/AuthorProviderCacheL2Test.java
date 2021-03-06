package api.entity.service.blogger;

import api.entity.blogger.Author;
import api.mapper.blogger.AuthorProviderMapper;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 二级缓存：SqlSessionFactory工厂级别，即在整个应用都有效，可在多个会话中有效
 */
public class AuthorProviderCacheL2Test {

    @Test
    public void testAuthorProviderCacheL2() throws IOException {

        String resource = "mybatis-config.xml";
        Logger logger = LoggerFactory.getLogger(AuthorServiceTest.class);

        InputStream is = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);

        for (int i = 0; i < 10; i++) {
            SqlSession session = sqlSessionFactory.openSession();
            AuthorProviderMapper mapper = session.getMapper(AuthorProviderMapper.class);

            Author author = new Author() {{
                this.setUserName("yitian");
            }};
            List<Author> authorList = mapper.getAuthorByCondition(author);
            // fastjson
            JSONArray object = JSONArray.parseArray(JSON.toJSONString(authorList));
            logger.info("author:{}", object);

            session.close();
        }
    }
}

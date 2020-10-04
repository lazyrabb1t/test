package xyz.lazyrabbit.feature.mybaits.origin;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import xyz.lazyrabbit.feature.mybaits.origin.entity.AccountInfo;
import xyz.lazyrabbit.feature.mybaits.origin.mapper.AccountInfoMapper;

import java.io.IOException;
import java.io.InputStream;

public class MybatiTests {
    public static void main(String[] args) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        AccountInfoMapper accountInfoMapper = sqlSession.getMapper(AccountInfoMapper.class);
        AccountInfo byId = accountInfoMapper.findById(51);
        System.out.println(byId.toString());

    }
}

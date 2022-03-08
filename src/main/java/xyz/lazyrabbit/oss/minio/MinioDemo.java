package xyz.lazyrabbit.oss.minio;

import io.minio.messages.Bucket;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年03月08日 14:19:00
 */
public class MinioDemo {
    public static void main(String[] args) throws Exception {
        // 上传一个文件
        String bucketName = "rabb";
        String filePath = "E:\\images\\th.jpg";
        File file = new File(filePath);
        MinioUtils.createBucket(bucketName);
        MinioUtils.putObject(bucketName, file.getName(), new FileInputStream(file));
        // 获取所有桶
        List<Bucket> bucketList = MinioUtils.getAllBuckets();
        for (Bucket bucket : bucketList) {
            System.out.println(bucket.creationDate() + ", " + bucket.name());
        }
    }
}

package xyz.lazyrabbit.oss.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年02月23日 16:02:00
 */
public class MinioUtils {


    private static MinioClient client;

    static {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("minio");

        client = MinioClient.builder()
                .endpoint(resourceBundle.getString("minio.endpoint"))
                .credentials(resourceBundle.getString("minio.accesskey"), resourceBundle.getString("minio.secretKey"))
                .build();
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public static void createBucket(String bucketName) {
        if (!client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     */
    @SneakyThrows
    public static List<Bucket> getAllBuckets() {
        return client.listBuckets();
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public static Optional<Bucket> getBucket(String bucketName) {
        return client.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    @SneakyThrows
    public static void removeBucket(String bucketName) {
        client.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public static String getObjectURL(String bucketName, String objectName, Integer expires) {
        Map<String, String> reqParams = new HashMap<String, String>();
        reqParams.put("response-content-type", "application/json");
        return client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(objectName)
                .expiry(expires, TimeUnit.HOURS)
                .extraQueryParams(reqParams)
                .build());
    }

    /**
     * 获取文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return 二进制流
     */
    @SneakyThrows
    public static InputStream getObject(String bucketName, String objectName) {
        return client.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 上传文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param stream     文件流
     */
    public static void putObject(String bucketName, String objectName, InputStream stream) throws Exception {
        client.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        stream, stream.available(), -1)
                        .build());
    }

    /**
     * 上传文件
     *
     * @param bucketName  bucket名称
     * @param objectName  文件名称
     * @param stream      文件流
     * @param size        大小
     * @param contextType 类型
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#putObject
     */
    public static void putObject(String bucketName, String objectName, InputStream stream, long size, String contextType) throws Exception {
        client.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        stream, size, -1)
                        .contentType(contextType)
                        .build());
    }

    /**
     * 获取文件信息
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @return
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#statObject
     */
    public static StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        return client.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception https://docs.minio.io/cn/java-client-api-reference.html#removeObject
     */
    public static void removeObject(String bucketName, String objectName) throws Exception {
        client.removeObject(
                RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
    }
//
//    /**
//     * 上传文件
//     *
//     * @param file       文件
//     * @param bucketName 存储桶
//     * @return
//     */
//    public static JSONObject uploadFile(MultipartFile file, String bucketName) throws Exception {
//        JSONObject res = new JSONObject();
//        res.put("code", 0);
//        // 判断上传文件是否为空
//        if (null == file || 0 == file.getSize()) {
//            res.put("msg", "上传文件不能为空");
//            return res;
//        }
//        // 判断存储桶是否存在
//        createBucket(bucketName);
//        // 文件名
//        String originalFilename = file.getOriginalFilename();
//        // 新的文件名
//        String fileName = bucketName + "_" + DateUtils.getYyyymmdd() + originalFilename.substring(originalFilename.lastIndexOf("."));
//        // 开始上传
//        client.putObject(bucketName, fileName, file.getInputStream(), file.getContentType());
//        res.put("code", 1);
//        res.put("msg", client..getEndpoint() + "/" + bucketName + "/" + fileName);
//        return res;
//    }
}

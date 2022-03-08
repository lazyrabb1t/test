package xyz.lazyrabbit.oss.fastdfs;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author guoxw
 * @Description TODO
 * @createTime 2022年03月08日 11:24:00
 */
public class FastDFSTest {

    public static final String TEST_FILE_PATH = "E:\\images\\carrot03.png";
    public static final String TEST_FILE_NAME = "carrot03.png";
    public static final String TEST_GROUP_NAME = "";
    public static final String TEST_REMOTE_FILE_NAME = "";
    public static final String TEST_FILE_OUT_PATH = "E:\\images\\carrot03.download.png";

    public static void main(String[] args) throws IOException, MyException {
        // 上传文件
        String[] uploadInfo = FastDFSClient.uploadFile(new File(TEST_FILE_PATH), TEST_FILE_NAME);
        System.out.println(Arrays.toString(uploadInfo));
        // 获取文件详情
        final FileInfo fileInfo = FastDFSClient.getFileInfo(uploadInfo[0], uploadInfo[1]);
        System.out.println(fileInfo.toString());
        // 获取文件数据
        NameValuePair[] metaDatas = FastDFSClient.getMetaData(uploadInfo[0], uploadInfo[1]);
        for (NameValuePair metaData : metaDatas) {
            System.out.println(metaData.getName() + ":" + metaData.getValue());
        }
        // 文件下载
        FastDFSClient.downloadFile(uploadInfo[0], uploadInfo[1], TEST_FILE_OUT_PATH);
        // 文件删除
        int result = FastDFSClient.deleteFile("group1", "M00/00/00/wKgKZl9xMdiAcOLdAADhaCZ_RF0096.jpg");
        System.out.println("result = " + result);
    }
}

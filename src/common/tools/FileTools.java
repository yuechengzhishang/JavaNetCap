package common.tools;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 
 * @author zhouyelin@chinamobile.com
 *
 */

public class FileTools {

	/**
	 * 判断指定文件是否存在
	 * @param path：文件路径，如：/storage/sdcard0/Manual/test.pdf
	 * @return true if and only if the file or directory denoted by this abstract pathname exists; false otherwise
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean fileIsExists(File file) {
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
	public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
	
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * 判断指定文件中是否存在指定的内容
	 * @param filePath
	 * @param contents
	 * @return
	 */
	@SuppressWarnings("resource")
	public static boolean isContainSpecialText(String url, String...contents) {
		boolean isContain = false;
		FileReader fr;
		try {
			fr = new FileReader(url);
			BufferedReader bf = new BufferedReader(fr);
			String temp = "";
			while(temp != null){
				temp = bf.readLine();
				if(temp != null){
					for(String content:contents){
						isContain = temp.contains(content);
						if(!isContain) break;
					}
				}
				if(isContain) break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isContain;
	}
	
	/**
	 * 读取文件内容,并返回一个字符串
	 * @param file
	 * @return
	 */
	public static String readFileToString(String file){
		String str = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bf = new BufferedReader(fr);
			String temp = "";
			while(temp != null){
				temp = bf.readLine();
				if(null != temp)
					str += temp;
				str += "\n";
			}
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/**
	 * 读取文件内容,并返回一个根据指定分隔符分割的字符串数组
	 * @param file
	 * @param separator : 分隔符，支持正则表达式
	 * @return
	 */
	public static String[] readFileToString(String file, String separator){
		String[] requests = readFileToString(file).split(separator);
//		for(String request : requests){
//			System.out.println("--------->" + request);
//		}
		return requests;
	}
	
	/**
	 * 将数据写入文件中
	 * @param fileName
	 * @param data
	 */
	public static void writeTxtFile(String fileName, String data) {
		File file = new File(fileName);
		boolean flag = true;
		try {
			if (!fileIsExists(fileName)) {
				flag = file.createNewFile();
			}
			if (flag) {
				FileWriter fileWritter = new FileWriter(file.getName(), true);
				BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				bufferWritter.write(data);
				bufferWritter.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String file = "D:\\workspace\\XYRJAPITest\\src\\test\\resources\\request_1.txt";
//		String content = readFileToString(file);
		//System.out.println(content);
		String REQUEST_SEPARATOR = "\n🐵🙈🙉\n";
		String[] requests = readFileToString(file, REQUEST_SEPARATOR);
		for(String request : requests){
			System.out.println("--------->" + request);
		}
		
		//String reconstitutedString = new String("a020259212266cf49cfb2ec8753cb874d9ff18db");
		//System.out.println(reconstitutedString);
	}
}
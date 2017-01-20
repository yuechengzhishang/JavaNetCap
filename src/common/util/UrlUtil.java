package common.util;

import java.io.UnsupportedEncodingException;
/**
 * url转码、解码
 *
 * @author lifq 
 * @date 2015-3-17 下午04:09:35
 */
public class UrlUtil {
    private final static String ENCODE = "UTF-8"; 
    /**
     * URL 解码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:09:51
     */
    public static String getURLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * URL 转码
     *
     * @return String
     * @author lifq
     * @date 2015-3-17 下午04:10:28
     */
    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * @return void
     * @author lifq
     * @date 2015-3-17 下午04:09:16
     */
    public static void main(String[] args) {
        String str = "测试1";
        System.out.println(getURLEncoderString(str));
        System.out.println(getURLDecoderString(str));
        String url = "%24class=hudson.tasks.LogRotator&quiet_period=5&scmCheckoutRetryCount=0&_.customWorkspace=&_.displayNameOrNull=&scm=0&authToken=&_.upstreamProjects=&ReverseBuildTrigger.threshold=SUCCESS&_.spec=&scmpoll_spec=&core%3Aapply=&Jenkins-Crumb=650bfd6e97ad4c03043824cd06d41833&json=%7B%22name%22%3A+%22myFirstJob%22%2C+%22description%22%3A+%22%22%2C+%22properties%22%3A+%7B%22stapler-class-bag%22%3A+%22true%22%2C+%22jenkins-model-BuildDiscarderProperty%22%3A+%7B%22specified%22%3A+false%2C+%22%22%3A+%220%22%2C+%22strategy%22%3A+%7B%22daysToKeepStr%22%3A+%22%22%2C+%22numToKeepStr%22%3A+%22%22%2C+%22artifactDaysToKeepStr%22%3A+%22%22%2C+%22artifactNumToKeepStr%22%3A+%22%22%2C+%22stapler-class%22%3A+%22hudson.tasks.LogRotator%22%2C+%22%24class%22%3A+%22hudson.tasks.LogRotator%22%7D%7D%2C+%22hudson-model-ParametersDefinitionProperty%22%3A+%7B%22specified%22%3A+false%7D%7D%2C+%22disable%22%3A+false%2C+%22concurrentBuild%22%3A+false%2C+%22hasCustomQuietPeriod%22%3A+false%2C+%22quiet_period%22%3A+%225%22%2C+%22hasCustomScmCheckoutRetryCount%22%3A+false%2C+%22scmCheckoutRetryCount%22%3A+%220%22%2C+%22blockBuildWhenUpstreamBuilding%22%3A+false%2C+%22blockBuildWhenDownstreamBuilding%22%3A+false%2C+%22hasCustomWorkspace%22%3A+false%2C+%22customWorkspace%22%3A+%22%22%2C+%22displayNameOrNull%22%3A+%22%22%2C+%22scm%22%3A+%7B%22value%22%3A+%220%22%7D%2C+%22core%3Aapply%22%3A+%22%22%2C+%22Jenkins-Crumb%22%3A+%22650bfd6e97ad4c03043824cd06d41833%22%7D&Submit=%E4%BF%9D%E5%AD%98";
        System.out.println(getURLDecoderString(url));
    }

}

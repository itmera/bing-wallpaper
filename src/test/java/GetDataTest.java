

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzlan.bingwallpaper.utils.HttpClient;

import java.io.IOException;
public class GetDataTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        String url = "https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=10&nc=1612409408851&pid=hp&FORM=BEHPTB&uhd=1&uhdwidth=3840&uhdheight=2160";
        String data = new HttpClient().getData(url);
        ObjectMapper  mapper =  new ObjectMapper();
        JsonNode json = mapper.readTree(data);
        System.out.println(json.get("images").get(1).get("startdate").asText());
        // System.out.println(json);
    }
}

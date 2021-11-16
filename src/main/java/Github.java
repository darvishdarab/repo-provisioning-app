import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Github {

    public static void createRepo(String name) throws IOException, ExecutionException, InterruptedException {
        String url = Constant.GITHUB_API_BASE_URL + "orgs/" + Constant.ORG_NAME + "/repos";

        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        // set repo name and private to true
        String json = "{\"name\":\"" + name + "\", \"private\": true}";
        StringEntity entity = new StringEntity(json);

        httpPost.setEntity(entity);

        httpPost.addHeader("Authorization", "token " + Constant.GITHUB_API );

        Future<HttpResponse> future = client.execute(httpPost, null);
        // and wait until a response is received
        HttpResponse response = future.get();
        client.close();

        System.out.println("--------RESPONSE OF CREATING REPO------");
        System.out.println();
        System.out.println(response.toString());
        System.out.println();
        System.out.println("----END OF RESPONSE OF CREATING REPO---");

    }
    public static void addMembers(String repoName, String user) throws IOException, ExecutionException, InterruptedException {
        String url = Constant.GITHUB_API_BASE_URL + "repos/" + Constant.ORG_NAME + "/" + repoName + "/collaborators/" + user;
        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();


        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        String json = "{\"permission\":\"admin\"}";
        StringEntity entity = new StringEntity(json);

        httpPut.setEntity(entity);


        httpPut.addHeader("Authorization", "token " + Constant.GITHUB_API);


        Future<HttpResponse> future = client.execute(httpPut, null);
        // and wait until a response is received
        HttpResponse response = future.get();
        client.close();

        System.out.println("--------RESPONSE OF ADDING A MEMBER------");
        System.out.println();
        System.out.println(response.toString());
        System.out.println();
        System.out.println("----END OF RESPONSE OF ADDING A MEMBER---");

    }

    public static void addTeam(String repoName, String teamID) throws IOException, ExecutionException, InterruptedException {
        String url = Constant.GITHUB_API_BASE_URL + "teams/" + teamID + "/repos/" + Constant.ORG_NAME + "/" + repoName;

        CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
        client.start();


        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Accept", "application/json");
        httpPut.setHeader("Content-type", "application/json");

        String json = "{\"permission\":\"admin\"}";
        StringEntity entity = new StringEntity(json);

        httpPut.setEntity(entity);


        httpPut.addHeader("Authorization", "token " + Constant.GITHUB_API);


        Future<HttpResponse> future = client.execute(httpPut, null);
        // and wait until a response is received
        HttpResponse response = future.get();
        client.close();

        System.out.println("--------RESPONSE OF TEAMS------");
        System.out.println();
        System.out.println(response.toString());
        System.out.println();
        System.out.println("----END OF RESPONSE OF TEAMS---");


    }


}

import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.get;

public class Server {

    final static int MAX_NUM_OF_PEOPLE = 20;


    public static void main(String[] args) {

        port(Constant.PORT);

        //homepage
        get("/", (req, res) -> {
            res.type("text/html; charset=UTF-8");
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "public/index.html");
        }, new VelocityTemplateEngine() );

        // create repo and add members
        post("/", (req, res) -> {
            List<String> firstNames = new ArrayList<>();
            List<String> lastNames = new ArrayList<>();
            List<String> jheds = new ArrayList<>();
            List<String> githubUsers = new ArrayList<>();

            for (int i = 1; i < MAX_NUM_OF_PEOPLE; ++i) {
                if (req.queryParams("firstName" + String.valueOf(i)) != null)
                    firstNames.add(req.queryParams("firstName" + String.valueOf(i)));
                if (req.queryParams("lastName" + String.valueOf(i)) != null)
                    lastNames.add(req.queryParams("lastName" + String.valueOf(i)));
                if (req.queryParams("jhed" + String.valueOf(i)) != null)
                    jheds.add(req.queryParams("jhed" + String.valueOf(i)));
                if (req.queryParams("githubUser" + String.valueOf(i)) != null)
                    githubUsers.add(req.queryParams("githubUser" + String.valueOf(i)));
            }

            String repoName = Constant.REPO_BASE_NAME;
            for (String jhed: jheds)
                repoName += "-" + jhed;
            Github.createRepo(repoName);
            for (String user: githubUsers)
                Github.addMembers(repoName, user);
            Github.addTeam(repoName, Constant.STAFF_TEAM_ID);

            res.redirect("ty");
            return "";
        });

        // page that gets displayed after from submission
        get("/ty", (req, res) -> {
            res.type("text/html; charset=UTF-8");
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "public/ty.html");
        }, new VelocityTemplateEngine() );
    }

}

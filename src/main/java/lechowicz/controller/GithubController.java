package lechowicz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.path.json.JsonPath;
import lechowicz.model.RepoModel;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

@Controller
public class GithubController {
    @GetMapping(value = "/repositories/{owner}/{repository-name}", produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<String> getDetails(@PathVariable String owner, @PathVariable("repository-name") String repoName) {
        String result = null;
        try {
            result = getResult(owner, repoName);
        } catch (HttpClientErrorException ex) {
            return new ResponseEntity<>("Repository doesn't exist", new HttpHeaders(), HttpStatus.NOT_FOUND);
        }

        RepoModel repoModel = getRepo(result);
        ObjectMapper mapper = new ObjectMapper();

        String repository = null;
        try {
            repository = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(repoModel);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong with server side.", new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(repository);
        return new ResponseEntity<>(repository, new HttpHeaders(), HttpStatus.OK);
    }

    private String getResult(String owner, String repoName){
        String uriRepo = String.format("https://api.github.com/repos/%s/%s", owner, repoName);
        String result = null;

        RestTemplate restTemplate = new RestTemplate();
        result = restTemplate.getForObject(uriRepo, String.class);

        return result;
    }

    private RepoModel getRepo(String resultFromGithub){
        String name = JsonPath.from(resultFromGithub).get("full_name");
        String description = JsonPath.from(resultFromGithub).get("description");
        String url = JsonPath.from(resultFromGithub).get("clone_url");
        int followers = JsonPath.from(resultFromGithub).getInt("stargazers_count");
        String dateAsString = JsonPath.from(resultFromGithub).get("created_at");

        Date date = Date.from(Instant.parse(dateAsString));

        return new RepoModel(name, description, url, followers, date);
    }

    //Najpierw autoryzujemy się za pomocą : GET https://github.com/login/oauth/authorize
    //Później możemy wysłąć zapytanie np. : GET https://api.github.com/repos/octocat/hello-world
    //                                                                       nazwa organizacji/ nazwa repozytorium
}

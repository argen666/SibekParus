package ru.sibek.parus;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Developer on 06.10.2014.
 */
public interface GitHubService {
    @GET("/users/{user}/repos")
    List<Repo> listRepos(@Path("user") String user);
}
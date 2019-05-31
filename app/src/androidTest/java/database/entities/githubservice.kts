
const val GITHUB_API_BASE_URI = "https://api.github.com/"


interface githubservice{

    @GET("/users/{user}/repos")
    fun getRepos(@Path("user") user:String):Deferred<Response<List<GithubRepo>>>

    companion object{
        fun getRetrofit():GithubService = Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URI)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(GitHubService::class.java)
    }
}
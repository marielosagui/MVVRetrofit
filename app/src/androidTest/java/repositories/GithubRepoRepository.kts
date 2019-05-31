
class GithubRepoRepository(private val repoDao:GitHubDAO){

        fun retrieveReposAsync(user:String): Deferred <Response<List<GitHubRepo>>>{
            return GitHubService.getRetrofit().getRepos(user)
        }

        @WorkerThread
        suspend fun insert(repo:GithubRepo){
            repoDao.insert(repo)
        }
    fun getAll():LiveData<List<GithubRepo>>{
        return repoDao.getAllRepos()
    }


    }

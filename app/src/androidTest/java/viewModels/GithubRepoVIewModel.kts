import sun.rmi.server.Dispatcher

class GitHubRepoViewModel(private val app: Application) : AndroidViewModel(app) {

    private val repository:GithubRepoRepository

    init {
        val repoDao=RoomDB.getInstance(app).repoDao()
        repository=GithubRepoRepository(repoDao)
    }

    private suspend fun insert(repo:GithubRepo)=repository.insert(repo)

    fun getAll():LiveData<List<GithubRepo>>{
        return repository.getAll()
    }

    private suspend fun nuke()=repository.nuke()


    fun retrieveRepo(user:String)=viewModelScope.launch{
        this@GitHubRepoViewModel.nuke()

        val response = repository.retrieveReposAsync(user).await()

        if(response.isSuccessful) with(response){

            //Inserta toda la lista en la base de datos
            this.body()?.forEach {
                this@GitHubRepoViewModel.insert(it)
            }

        }else with(response){

            println(this.code())
            when(this.code()){
                //Aqui pueden evaluarse todos los codigos HTTP
                404->{
                    //Muestra un estado de error en caso no encuentre el usuario
                    Toast.makeText(app, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    ...

}
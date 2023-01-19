class SsoUsers {
    def sso_users_upsert_img
    def sso_users_delete_img
    def sso_users_upsert = "sso_users_upsert"
    def sso_users_delete = "sso_users_delete"
    def sso_users_upsert_registry
    def sso_users_delete_registry
    def ssoUsersUpsertImage
    def ssoUsersDeleteImage

    public void setEnvironment() {
        sso_users_upsert_registry = "axelmastroianni/${this.sso_users_upsert}"
        sso_users_delete_registry = "axelmastroianni/${this.sso_users_delete}"
        ssoUsersUpsertImage = ''
        ssoUsersDeleteImage = ''
    }

    public void initializeImages(def script) {
        sso_users_delete_img = sso_users_delete_registry + ":${script.env.BUILD_ID}"
        sso_users_upsert_img = sso_users_upsert_registry + ":${script.env.BUILD_ID}"
        println("${sso_users_delete_img}")
        println("${sso_users_upsert_img}")
    }

    public void buildDockerImageDelete(def script) {
        ssoUsersDeleteImage = script.docker.build("${sso_users_delete_img}", "-f ${script.env.WORKSPACE}/sso_users/delete_index/Dockerfile .")
    }

    public void buildDockerImagePopulate(def script) {
        ssoUsersUpsertImage = script.docker.build("${sso_users_upsert_img}", "-f ${script.env.WORKSPACE}/sso_users/populate_index/Dockerfile .")
    }

    public void buildDockerImagesReset(def script) {
        ssoUsersUpsertImage = script.docker.build("${sso_users_upsert_img}", "-f ${script.env.WORKSPACE}/sso_users/populate_index/Dockerfile .")
        ssoUsersDeleteImage = script.docker.build("${sso_users_delete_img}", "-f ${script.env.WORKSPACE}/sso_users/delete_index/Dockerfile .")
    }

    public void runDockerImageDelete(def script) {
        script.powershell "docker run --name ${sso_users_delete} ${sso_users_delete_img}"
    }

    public void runDockerImagePopulate(def script) {
        script.powershell "docker run --name ${sso_users_upsert} ${sso_users_upsert_img}"
    }

    public void runDockerImagesReset(def script) {
        script.powershell "docker run --name ${sso_users_delete} ${sso_users_delete_img}"
        script.powershell "docker run --name ${sso_users_upsert} ${sso_users_upsert_img}"
    }

    public void stopDockerContainerDelete(def script) {
        script.powershell "docker stop ${sso_users_delete}"
    }

    public void stopDockerContainerPopulate(def script) {
        script.powershell "docker stop ${sso_users_upsert}"
    }

    public void stopDockerContainersReset(def script) {
        script.powershell "docker stop ${sso_users_delete}"
        script.powershell "docker stop ${sso_users_upsert}"
    }

    public void deleteDockerContainerDelete(def script) {
        script.powershell "docker rm ${sso_users_delete}"
    }

    public void deleteDockerContainerPopulate(def script) {
        script.powershell "docker rm ${sso_users_upsert}"
    }

    public void deleteDockerContainersReset(def script) {
        script.powershell "docker rm ${sso_users_delete}"
        script.powershell "docker rm ${sso_users_upsert}"
    }
}

SsoUsers createSsoUsers() {
    new SsoUsers()
}

return this
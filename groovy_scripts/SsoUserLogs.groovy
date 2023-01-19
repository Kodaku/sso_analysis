class SsoUserLogs {
    def sso_user_logs_upsert_img
    def sso_user_logs_delete_img
    def sso_user_logs_upsert = "sso_user_logs_upsert"
    def sso_user_logs_delete = "sso_user_logs_delete"
    def sso_user_logs_upsert_registry
    def sso_user_logs_delete_registry
    def ssoUserLogsUpsertImage
    def ssoUserLogsDeleteImage

    public void setEnvironment() {
        sso_user_logs_upsert_registry = "axelmastroianni/${this.sso_user_logs_upsert}"
        sso_user_logs_delete_registry = "axelmastroianni/${this.sso_user_logs_delete}"
        ssoUserLogsUpsertImage = ''
        ssoUserLogsDeleteImage = ''
    }

    public void initializeImages(def script) {
        sso_user_logs_delete_img = sso_user_logs_delete_registry + ":${script.env.BUILD_ID}"
        sso_user_logs_upsert_img = sso_user_logs_upsert_registry + ":${script.env.BUILD_ID}"
        println("${sso_user_logs_delete_img}")
        println("${sso_user_logs_upsert_img}")
    }

    public void buildDockerImageDelete(def script) {
        ssoUserLogsDeleteImage = script.docker.build("${sso_user_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/delete_index/Dockerfile .")
    }

    public void buildDockerImagePopulate(def script) {
        ssoUserLogsUpsertImage = script.docker.build("${sso_user_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/populate_index/Dockerfile .")
    }

    public void buildDockerImagesReset(def script) {
        ssoUserLogsUpsertImage = script.docker.build("${sso_user_logs_upsert_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/populate_index/Dockerfile .")
        ssoUserLogsDeleteImage = script.docker.build("${sso_user_logs_delete_img}", "-f ${script.env.WORKSPACE}/sso_user_logs/delete_index/Dockerfile .")
    }

    public void runDockerImageDelete(def script) {
        script.powershell "docker run --name ${sso_user_logs_delete} ${sso_user_logs_delete_img}"
    }

    public void runDockerImagePopulate(def script) {
        script.powershell "docker run --name ${sso_user_logs_upsert} ${sso_user_logs_upsert_img}"
    }

    public void runDockerImagesReset(def script) {
        script.powershell "docker run --name ${sso_user_logs_delete} ${sso_user_logs_delete_img}"
        script.powershell "docker run --name ${sso_user_logs_upsert} ${sso_user_logs_upsert_img}"
    }

    public void stopDockerContainerDelete(def script) {
        script.powershell "docker stop ${sso_user_logs_delete}"
    }

    public void stopDockerContainerPopulate(def script) {
        script.powershell "docker stop ${sso_user_logs_upsert}"
    }

    public void stopDockerContainersReset(def script) {
        script.powershell "docker stop ${sso_user_logs_delete}"
        script.powershell "docker stop ${sso_user_logs_upsert}"
    }

    public void deleteDockerContainerDelete(def script) {
        script.powershell "docker rm ${sso_user_logs_delete}"
    }

    public void deleteDockerContainerPopulate(def script) {
        script.powershell "docker rm ${sso_user_logs_upsert}"
    }

    public void deleteDockerContainersReset(def script) {
        script.powershell "docker rm ${sso_user_logs_delete}"
        script.powershell "docker rm ${sso_user_logs_upsert}"
    }
}

SsoUserLogs createSsoUserLogs() {
    new SsoUserLogs()
}

return this
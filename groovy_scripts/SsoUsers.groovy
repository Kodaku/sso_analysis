class SsoUsers {
    def sso_users_upsert_img
    def sso_users_delete_img
    def sso_users_upsert = "sso_users_upsert"
    def sso_users_delete = "sso_users_delete"
    def sso_users_upsert_registry
    def sso_users_delete_registry
    def ssoUsersUpsertImage
    def ssoUsersDeleteImage
    def opzioneReset = "Reset"
    def opzioneElimina = "Elimina"
    def opzionePopola = "Popola"

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

    public void buildDockerImages(def script) {
        initializeImages(script)
        if (script.params.sso_users == opzioneReset) {
            ssoUsersUpsertImage = script.docker.build("${sso_users_upsert_img}", "-f ${script.env.WORKSPACE}/sso_users/populate_index/Dockerfile .")
            ssoUsersDeleteImage = script.docker.build("${sso_users_delete_img}", "-f ${script.env.WORKSPACE}/sso_users/delete_index/Dockerfile .")
        } else if (script.params.sso_users == opzioneElimina) {
            ssoUsersDeleteImage = script.docker.build("${sso_users_delete_img}", "-f ${script.env.WORKSPACE}/sso_users/delete_index/Dockerfile .")
        } else if (script.params.sso_users == opzionePopola) {
            ssoUsersUpsertImage = script.docker.build("${sso_users_upsert_img}", "-f ${script.env.WORKSPACE}/sso_users/populate_index/Dockerfile .")
        }
    }

    public void runDockerContainers(def script) {
        if (script.params.sso_users == opzioneReset) {
            script.powershell "docker run --name ${sso_users_delete} ${sso_users_delete_img}"
            script.powershell "docker run --name ${sso_users_upsert} ${sso_users_upsert_img}"
        } else if (script.params.sso_users == opzioneElimina) {
            script.powershell "docker run --name ${sso_users_delete} ${sso_users_delete_img}"
        } else if (script.params.sso_users == opzionePopola) {
            script.powershell "docker run --name ${sso_users_upsert} ${sso_users_upsert_img}"
        }
    }

    public void stopDockerContainers(def script) {
        if (script.params.sso_users == opzioneReset) {
            script.powershell "docker stop ${sso_users_delete}"
            script.powershell "docker stop ${sso_users_upsert}"
        } else if (script.params.sso_users == opzioneElimina) {
            script.powershell "docker stop ${sso_users_delete}"
        } else if (script.params.sso_users == opzionePopola) {
            script.powershell "docker stop ${sso_users_upsert}"
        }
    }

    public void removeDockerContainers(def script) {
        if (script.params.sso_users == opzioneReset) {
            script.powershell "docker rm ${sso_users_delete}"
            script.powershell "docker rm ${sso_users_upsert}"
        } else if (script.params.sso_users == opzioneElimina) {
            script.powershell "docker rm ${sso_users_delete}"
        } else if (script.params.sso_users == opzionePopola) {
            script.powershell "docker rm ${sso_users_upsert}"
        }
    }
}

SsoUsers createSsoUsers() {
    new SsoUsers()
}

return this
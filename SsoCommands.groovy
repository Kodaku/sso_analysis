def sso_commands_upsert_img
def sso_commands_delete_img
def sso_commands_upsert = "sso_commands_upsert"
def sso_commands_delete = "sso_commands_delete"
def sso_commands_upsert_registry
def sso_commands_delete_registry
def ssoCommandsUpsertImage
def ssoCommandsDeleteImage

def setEnvironment() {
    sso_commands_upsert_registry = "axelmastroianni/${this.sso_commands_upsert}"
    sso_commands_delete_registry = "axelmastroianni/${this.sso_commands_delete}"
    ssoCommandsUpsertImage = ''
    ssoCommandsDeleteImage = ''
}

def initializeImages() {
    sso_commands_delete_img = sso_commands_delete_registry + ":${env.BUILD_ID}"
    sso_commands_upsert_img = sso_commands_upsert_registry + ":${env.BUILD_ID}"
    println("${this.sso_commands_delete_img}")
    println("${this.sso_commands_upsert_img}")
}

def buildDockerImages() {
    ssoCommandsUpsertImage = docker.build("${this.sso_commands_upsert_img}", "-f ${env.WORKSPACE}/sso_commands/populate_index/Dockerfile .")
    ssoCommandsDeleteImage = docker.build("${this.sso_commands_delete_img}", "-f ${env.WORKSPACE}/sso_commands/delete_index/Dockerfile .")
}

def runDockerImages() {
    powershell "docker run --name ${this.sso_commands_delete} ${this.sso_commands_delete_img}"
    powershell "docker run --name ${this.sso_commands_upsert} ${sso_commands_upsert_img}"
}

def stopDockerContainers() {
    powershell "docker stop ${this.sso_commands_delete}"
    powershell "docker stop ${this.sso_commands_upsert}"
}

def deleteDockerContainers() {
    powershell "docker rm ${this.sso_commands_delete}"
    powershell "docker rm ${this.sso_commands_upsert}"
}

return this
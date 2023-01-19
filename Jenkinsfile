def ssoCommands
def opzioneReset = "Reset"
def opzioneElimina = "Elimina"
def opzionePopola = "Popola"

pipeline {
//     environment {
//         ssoCommands.setEnvironment()
//     }
    agent any

    stages {
        stage("Init") {
            steps {
                script {
                    def commandsScript = load "SsoCommands.groovy"
                    ssoCommands = commandsScript.createSsoCommands()
                    ssoCommands.setEnvironment()
                }
            }
        }
        stage("Build image") {
            steps {
                script {
                    ssoCommands.initializeImages(this)
                    if (params.sso_commands == opzioneReset) {
                        ssoCommands.buildDockerImagesReset(this)
                    } else if (params.sso_commands == opzioneElimina) {
                        ssoCommands.buildDockerImageDelete(this)
                    } else if (params.sso_commands == opzionePopola) {
                        ssoCommands.buildDockerImagePopulate(this)
                    }
                }
            }
        }

        stage("Testing - running in Jenkins node") {
            steps {
                script {
                    if (params.sso_commands == opzioneReset) {
                        ssoCommands.runDockerImagesReset(this)
                    } else if (params.sso_commands == opzioneElimina) {
                        ssoCommands.runDockerImageElimina(this)
                    } else if (params.sso_commands == opzionePopola) {
                        ssoCommands.runDockerImagePopola(this)
                    }
                }
            }
        }

        stage("Stopping running container") {
            steps {
                script {
                    if (params.sso_commands == opzioneReset) {
                        ssoCommands.stopDockerContainersReset(this)
                    } else if (params.sso_commands == opzioneElimina) {
                        ssoCommands.stopDockerContainerElimina(this)
                    } else if (params.sso_commands == opzionePopola) {
                        ssoCommands.stopDockerContainerPopola(this)
                    }
                }
            }
        }

        stage("Removing the container") {
            steps {
                script {
                    if (params.sso_commands == opzioneReset) {
                        ssoCommands.deleteDockerContainersReset(this)
                    } else if (params.sso_commands == opzioneElimina) {
                        ssoCommands.deleteDockerContainerElimina(this)
                    } else if (params.sso_commands == opzionePopola) {
                        ssoCommands.deleteDockerContainerPopola(this)
                    }
                }
            }
        }
    }
}
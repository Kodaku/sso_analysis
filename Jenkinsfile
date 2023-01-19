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
                    if (params.is_sso_commands) {
                        ssoCommands = commandsScript.createSsoCommands()
                        ssoCommands.setEnvironment()
                    }
                }
            }
        }
        stage("Build image") {
            steps {
                script {
                    if (params.is_sso_commands) {
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
        }

        stage("Testing - running in Jenkins node") {
            steps {
                script {
                    if (params.is_sso_commands) {
                        if (params.sso_commands == opzioneReset) {
                            ssoCommands.runDockerImagesReset(this)
                        } else if (params.sso_commands == opzioneElimina) {
                            ssoCommands.runDockerImageDelete(this)
                        } else if (params.sso_commands == opzionePopola) {
                            ssoCommands.runDockerImagePopulate(this)
                        }
                    }
                }
            }
        }

        stage("Stopping running container") {
            steps {
                script {
                    if (params.is_sso_commands) {
                        if (params.sso_commands == opzioneReset) {
                            ssoCommands.stopDockerContainersReset(this)
                        } else if (params.sso_commands == opzioneElimina) {
                            ssoCommands.stopDockerContainerDelete(this)
                        } else if (params.sso_commands == opzionePopola) {
                            ssoCommands.stopDockerContainerPopulate(this)
                        }
                    }
                }
            }
        }

        stage("Removing the container") {
            steps {
                script {
                    if (params.is_sso_commands) {
                        if (params.sso_commands == opzioneReset) {
                            ssoCommands.deleteDockerContainersReset(this)
                        } else if (params.sso_commands == opzioneElimina) {
                            ssoCommands.deleteDockerContainerDelete(this)
                        } else if (params.sso_commands == opzionePopola) {
                            ssoCommands.deleteDockerContainerPopulate(this)
                        }
                    }
                }
            }
        }
    }
}
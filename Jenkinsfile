def ssoCommands

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
                    if (params.sso_commands == "Reset") {
                        ssoCommands.buildDockerImages(this)
                    }
                }
            }
        }

        stage("Testing - running in Jenkins node") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        ssoCommands.runDockerImages()
                    }
                }
            }
        }

        stage("Stopping running container") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        ssoCommands.stopDockerContainers()
                    }
                }
            }
        }

        stage("Removing the container") {
            steps {
                script {
                    if (params.sso_commands == "Reset") {
                        ssoCommands.deleteDockerContainers()
                    }
                }
            }
        }
    }
}
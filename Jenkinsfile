def sso_commands

pipeline {
//     environment {
//         ssoCommands.setEnvironment()
//     }
    agent any

    stages {
        stage("Init") {
            steps {
                script {
                    ssoCommands = load "SsoCommands.groovy"
                    ssoCommands.setEnvironment()
                }
            }
        }
        stage("Build image") {
            steps {
                script {
                    ssoCommands.initializeImages()
                    if (params.sso_commands == "Reset") {
                        ssoCommands.buildDockerImages()
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
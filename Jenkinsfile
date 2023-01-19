def sso

pipeline {
    agent any

    stages {
        stage("Init") {
            steps {
                script {
                    def ssoScript = load "./groovy_scripts/Sso.groovy"
                    sso = ssoScript.createSso()
                    sso.setup(this)
                    sso.setEnvironment(this)
                }
            }
        }

        stage("Build image") {
            steps {
                script {
                    sso.buildDockerImages(this)
                }
            }
        }

        stage("Testing - running in Jenkins node") {
            steps {
                script {
                    sso.runDockerContainers(this)
                }
            }
        }

        stage("Stopping running container") {
            steps {
                script {
                    sso.stopDockerContainers(this)
                }
            }
        }

        stage("Removing the container") {
            steps {
                script {
                    sso.removeDockerContainers(this)
                }
            }
        }
    }
}
pipeline {
  agent { dockerfile true }
  stages {
    stage('Hello JMP') {
        steps {
            echo 'Hi EMAP JMP!!!'
        }
    }
    stage('Compile'){
        steps {
            script {
                sh './gradlew clean'
                sh './gradlew build'
            }
        }
    }
    stage('Build Docker Image') {
        steps {
            node('docker build') {
                checkout scm
                docker.build("spring_ocpp_16_j:${env.BUILD_ID}")

            }
        }
    }
  }
  post {
    always {
        cleanWs()
    }
  }
}

pipeline {
  agent any
  stages {
    stage('Hello JMP') {
        steps {
            echo 'Hi EMAP JMP!!!'
        }
    }
//     stage('git  clean & clone') {
//         steps {
//             git branch: 'main',  url: 'https://github.com/kalosha/SpringOCPP16J.git'
//         }
//     }
    stage('Compile'){
        steps{
            script {
                sh './gradlew clean'
                sh './gradlew build'
            }
        }
    }
  }
}

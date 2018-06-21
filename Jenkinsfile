pipeline {
  agent none
  stages {
    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t demeter .'
      }
    }  
    stage('gradle test') {
      agent {
        docker {
          image 'demeter'
        }
      }
      steps {
        sh './gradlew --stacktrace --info clean test'
      }
    }
  }
}
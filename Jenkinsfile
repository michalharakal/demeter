pipeline {
  agent none
  environment {
        DEMETER_UI_APP_HOCKEYAPP_API_TOKEN = credentials('demeter_ui-app-hockeyapp-api-token')
  }
 
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

    // https://github.com/gini/gini-vision-lib-android/blob/master/Jenkinsfile
    stage('Upload Apps to Hockeyapp') {
            steps {
                step([$class: 'HockeyappRecorder', 
  
                  sh "echo ${DEMETER_UI_APP_HOCKEYAPP_API_TOKEN}" 

                  applications: [[apiToken: DEMETER_UI_APP_HOCKEYAPP_API_TOKEN, downloadAllowed: true, 
                  //dsymPath: 'screenapiexample/build/outputs/mapping/release/mapping.txt', 
                  filePath: 'mobile-ui/build/outputs/apk/debug/mobile-ui-debug.apk', 
                  mandatory: false, 
                  notifyTeam: false, 
                  releaseNotesMethod: 
                  [$class: 'ChangelogReleaseNotes'], uploadMethod: [$class: 'AppCreation', publicPage: false]]], debugMode: false, failGracefully: false])
            }
        }
  }
}
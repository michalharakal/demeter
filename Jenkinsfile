pipeline {
  agent none
  environment {
        DEMETER_UI_APP_HOCKEYAPP_API_TOKEN = credentials('demeter_ui-app-hockeyapp-api-token')
  }
 
  stages {
    stage('Import Pipeline Libraries') {
        steps{
             library 'android-tools'
        }
    }
    stage('Docker Build') {
      agent any
      steps {
        sh 'docker build -t demeter .'
      }
    }  
    stage('Gradle test') {
      agent {
        docker {
          image 'demeter'
        }
      }
      steps {
        sh './gradlew --stacktrace --info clean test'
      }
    }

    stage('Gradle build') {
      agent {
        docker {
          image 'demeter'
        }
      }
      steps {
        sh './gradlew --stacktrace --info clean assembleDebug'
        archiveArtifacts 'mobile-ui/build/outputs/apk/debug/mobile-ui-debug.apk'    
      }
    }

    // https://github.com/gini/gini-vision-lib-android/blob/master/Jenkinsfile
    stage('Upload Apps to Hockeyapp') {
            steps {
                step([$class: 'HockeyappRecorder', 
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
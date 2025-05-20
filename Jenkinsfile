pipeline {
    agent any

    environment {
        IMAGE_NAME = "chat-backend/websocket-server/${env.JOB_BASE_NAME}"
        TAG = "${BUILD_NUMBER}"
        HARBOR_CREDENTIALS_ID = "Harbor"
        SERVICE_NAME = "${env.JOB_BASE_NAME}"
        HARBOR_URL = "harbor.shoong.store"
    }

    stages {
        stage('Build JAR') {
            steps {
                dir('chat-backend') {
                    sh '''
                        echo "gradlew 존재 여부:"
                        ls -l gradlew || { echo "❌ gradlew not found"; exit 1; }

                        echo "실행 권한 부여:"
                        chmod +x gradlew

                        echo "Gradle 빌드 시작:"
                        ./gradlew clean build -x test
                    '''
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME}:latest ."
                sh "docker tag ${IMAGE_NAME}:latest ${IMAGE_NAME}:${TAG}"
            }
        }

        stage('Login to Harbor') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${HARBOR_CREDENTIALS_ID}", usernameVariable: 'HARBOR_USER', passwordVariable: 'HARBOR_PASS')]) {
                    sh "echo \$HARBOR_PASS | docker login ${HARBOR_URL} -u \$HARBOR_USER --password-stdin"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                sh "docker push ${IMAGE_NAME}:latest"
                sh "docker push ${IMAGE_NAME}:${TAG}"
            }
        }

        stage('Update Manifest') {
            steps {
                script {
                    sh """
                        rm -rf k8s-manifests
                        git clone https://github.com/your-org/k8s-manifests.git
                        cd k8s-manifests/apps/websocket
                        sed -i 's|image: .*|image: ${IMAGE_NAME}:${TAG}|' deployment.yaml
                        git config user.name "jenkins-bot"
                        git config user.email "jenkins@shoong.com"
                        git commit -am "Update websocket image to ${TAG}"
                        git push origin main
                    """
                }
            }
        }
    }

    post {
        success {
            echo "🎀 ${env.SERVICE_NAME} WebSocket 서버 자동 빌드 완료!"
        }
        failure {
            echo "😡 ${env.SERVICE_NAME} WebSocket 빌드 실패!"
        }
    }
}

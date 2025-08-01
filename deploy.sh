#!/bin/bash

# EC2 배포 스크립트 (GitHub Packages 이미지 사용)
set -e

echo "🚀 Starting deployment..."

# 환경 변수 파일 확인
if [ ! -f .env ]; then
    echo "❌ .env file not found. Please create .env file with database credentials."
    echo "You can copy from .env.example and fill in your credentials."
    echo "Make sure to set all required environment variables including GITHUB_TOKEN"
    exit 1
fi

# Docker 설치 확인
if ! command -v docker &> /dev/null; then
    echo "📦 Installing Docker..."
    sudo yum update -y
    sudo yum install -y docker
    sudo systemctl start docker
    sudo systemctl enable docker
    sudo usermod -aG docker ec2-user
    echo "✅ Docker installed successfully"
    echo "⚠️  Please log out and log back in for Docker group changes to take effect"
    exit 0
fi

# 환경변수 로드 및 확인
echo "🔧 Loading environment variables..."
source .env

if [ -z "$GITHUB_TOKEN" ] || [ -z "$GITHUB_USERNAME" ]; then
    echo "⚠️  GitHub credentials not found in .env file"
    echo "Please add GITHUB_TOKEN and GITHUB_USERNAME to your .env file"
    echo "Generate token at: https://github.com/settings/tokens"
    echo "Required permissions: read:packages"
    exit 1
else
    echo "✅ GitHub credentials loaded from .env file"
fi

# GitHub Container Registry 로그인 (환경변수 사용)
echo "🔐 Logging in to GitHub Container Registry..."
echo "$GITHUB_TOKEN" | docker login ghcr.io -u "$GITHUB_USERNAME" --password-stdin

# 기존 컨테이너 중지
echo "🛑 Stopping existing containers..."
docker-compose down || true

# 최신 이미지 pull
echo "📥 Pulling latest image from GitHub Packages..."
docker-compose pull

# 컨테이너 시작
echo "🚀 Starting containers..."
docker-compose up -d

# 컨테이너 상태 확인
echo "📊 Checking container status..."
docker-compose ps

# 애플리케이션 시작 대기
echo "⏳ Waiting for application to start..."
sleep 30

# 헬스체크
echo "🏥 Performing health check..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Application is healthy and running!"
    echo "🌐 Application URL: http://43.203.245.248:8080"
    echo "📋 Swagger UI: http://43.203.245.248:8080/swagger-ui.html"
else
    echo "❌ Health check failed. Checking logs..."
    docker-compose logs --tail=50 app
    exit 1
fi

echo "🎉 Deployment completed successfully!"

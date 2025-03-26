# blog
# イメージビルド
docker build -t jupyterhub-c .

# 起動
docker run -p 8000:8000 --name my-jupyterhub jupyterhub-c

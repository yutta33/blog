FROM python:3.11-slim

# --- 必要パッケージのインストール ---
RUN apt-get update && apt-get install -y \
    curl \
    vim \
    build-essential \
    gcc \
    g++ \
    cmake \
    make \
    git \
    libcurl4-openssl-dev \
    gnuplot \
    sudo \
    npm \
    nodejs \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# --- Node.jsとconfigurable-http-proxyのインストール ---
RUN apt-get update && apt-get install -y curl \
    && curl -fsSL https://deb.nodesource.com/setup_22.x | bash - \
    && apt-get install -y nodejs \
    && npm install -g configurable-http-proxy

# --- JupyterHub + Cカーネルのインストール ---
RUN pip install --upgrade pip \
    && pip install jupyterhub notebook jupyterlab jupyter-c-kernel

#RUN pip install --upgrade pip \
#    && pip install jupyterhub notebook jupyterlab jupyter-c-kernel \
#    && python3 -m jupyter_c_kernel install

RUN install_c_kernel --sys-prefix

# --- ユーザー作成 (例: testユーザー) ---
RUN useradd -m test && echo "test:test" | chpasswd && adduser test sudo

# --- 作業ディレクトリ ---
WORKDIR /srv/jupyterhub

# --- JupyterHubの設定ファイル jupyterhub_config.py を明示的に作成
#RUN mkdir -p /etc/jupyterhub \
#    && jupyterhub --generate-config -f /etc/jupyterhub/jupyterhub_config.py
#RUN echo "c.JupyterHub.authenticator_class = 'pam'" >> /etc/jupyterhub/jupyterhub_config.py
#RUN echo "c.JupyterHub.bind_url = 'http://:8000'" >> /etc/jupyterhub/jupyterhub_config.py
RUN mkdir -p /etc/jupyterhub && \
    echo "c.JupyterHub.bind_url = 'http://:8000'" > /etc/jupyterhub/jupyterhub_config.py && \
    echo "c.Authenticator.allowed_users = {'test'}" >> /etc/jupyterhub/jupyterhub_config.py


# カーネルパスを登録（必要なら）
ENV PATH="/home/test/.local/bin:$PATH"

# --- ポート開放 ---
EXPOSE 8000

# --- 起動コマンド ---
CMD ["jupyterhub", "-f", "/etc/jupyterhub/jupyterhub_config.py"]

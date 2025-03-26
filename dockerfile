FROM python:3.11-slim

# --- 必要パッケージのインストール ---
RUN apt-get update && apt-get install -y \
    curl \
    gcc \
    g++ \
    make \
    git \
    libcurl4-openssl-dev \
    gnuplot \
    sudo \
    npm \
    nodejs \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# --- cJSON のビルドとインストール ---
RUN git clone https://github.com/DaveGamble/cJSON.git /opt/cJSON && \
    cd /opt/cJSON && mkdir build && cd build && \
    cmake .. && make && make install && \
    ldconfig

# --- JupyterHub + Cカーネルのインストール ---
RUN pip install jupyterhub notebook jupyterlab \
    && pip install jupyter-c-kernel \
    && python3 -m jupyter_c_kernel.install

# --- ユーザー作成 (例: testユーザー) ---
RUN useradd -m test && echo "test:test" | chpasswd && adduser test sudo

# --- 作業ディレクトリ ---
WORKDIR /srv/jupyterhub

# --- ポート開放 ---
EXPOSE 8000

# --- 起動コマンド ---
CMD ["jupyterhub", "--ip", "0.0.0.0", "--port", "8000"]

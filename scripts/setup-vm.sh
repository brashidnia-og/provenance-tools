#!/bin/bash

# Initial update repository
yes | sudo apt-get update

# Get docker repo
yes | sudo apt-get install \
	ca-certificates \
	curl \
	gnupg \
	lsb-release
curl -fsSL https://download.docker.com/linux/debian/gpg | sudo gpg --dearmor -o /usr/share/keyrings/docker-archive-keyring.gpg
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/docker-archive-keyring.gpg] https://download.docker.com/linux/debian \
  $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null

# Update the repository
yes | sudo apt-get update

# Install wget
yes | sudo apt-get install wget

# Install go
#sudo snap install go --classic
wget -c https://dl.google.com/go/go1.17.7.linux-amd64.tar.gz -O - | sudo tar -xz -C /usr/local
echo 'export PATH=$PATH:/usr/local/go/bin' >> /etc/environment
echo 'export GOROOT="/usr/local/go"'
source /etc/environment
sudo update-alternatives --install "/usr/bin/go" "go" "/usr/local/go/bin/go" 0
sudo update-alternatives --set go /usr/local/go/bin/go
sudo mkdir -p ~/.go
echo 'export GOPATH=~/.go' >> ~/.bash_profile
echo 'export PATH=$PATH:$GOPATH/bin' >> ~/.bash_profile
source ~/.bash_profile

sudo mkdir -p ~/go/pkg
sudo chmod -R 777 ~/go/pkg

# Install gcc
yes | sudo apt install build-essential

# Install make
yes | sudo apt install make

# Install git
yes | sudo apt install git-all
git --version

# Install leveldb
yes | sudo apt-get install libsnappy-dev wget curl build-essential cmake gcc sqlite3
VER=$(curl -s https://api.github.com/repos/google/leveldb/releases/latest | grep tag_name | cut -d '"' -f 4)
wget https://github.com/google/leveldb/archive/${VER}.tar.gz -O leveldb.tar.gz
tar xvf leveldb.tar.gz
cd leveldb*/
mkdir -p build && cd build
cmake -DCMAKE_BUILD_TYPE=Release .. && cmake --build .
cd ~
yes | sudo apt-get install libleveldb-dev

# Install docker
yes | sudo apt install docker.io
yes | sudo apt-get install docker-ce docker-ce-cli containerd.io

# Install docker-compose
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
docker-compose --version

# Clone provenance
sudo mkdir -p ~/go/src/github.com/provenance-io
cd ~/go/src/github.com/provenance-io
yes | sudo git clone https://github.com/provenance-io/provenance.git
cd provenance
git checkout tags/v1.8.0 -b v1.8.0
pwd

# Build and run localnet
sudo make clean
sudo make build
sudo make install
sudo make localnet-start

alias provenanced="sudo ~/go/src/github.com/provenance-io/provenance/build/provenanced"

# Additional utilities
# output formatter
yes | sudo apt install jq

# For backend support
# Sensors (temps)
yes | sudo apt install lm-sensors

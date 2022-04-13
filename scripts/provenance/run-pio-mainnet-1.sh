#!/bin/bash

# May need to run `sudo su` to assume root permissions to run `cosmovisor`

export GO_PATH="/home/boba/.go"
export PIO_MAINNET_HOME="/var/provenance/pio-mainnet-1"
export DAEMON_NAME="provenanced"
export DAEMON_HOME="${PIO_MAINNET_HOME}"
export DAEMON_ALLOW_DOWNLOAD_BINARIES="true"
export DAEMON_RESTART_AFTER_UPGRADE="true"

script -a -c '$GO_PATH/bin/cosmovisor start \
--home $PIO_MAINNET_HOME \
--p2p.seeds 4bd2fb0ae5a123f1db325960836004f980ee09b4@seed-0.provenance.io:26656, 048b991204d7aac7209229cbe457f622eed96e5d@seed-1.provenance.io:26656 \
--x-crisis-skip-assert-invariants \
--minimum-gas-prices 1905nhash \
--log_format json' \
-f /var/provenance/pio-mainnet-1/log/node.log
#!/bin/bash

# May need to run `sudo su` to assume root permissions to run `cosmovisor`

export GO_PATH="/home/boba/.go"
export PIO_MAINNET_HOME="/var/provenance/pio-mainnet-1"
export DAEMON_NAME="provenanced"
export DAEMON_HOME="${PIO_HOME}"
export DAEMON_ALLOW_DOWNLOAD_BINARIES="true"
export DAEMON_RESTART_AFTER_UPGRADE="true"

script -a -c '$GO_PATH/bin/cosmovisor start \
--home $PIO_MAINNET_HOME \
--p2p.seeds TODO \
--x-crisis-skip-assert-invariants \
--minimum-gas-prices 1905nhash \
--log_format json' \
-f /var/provenance/pio-mainnet-1/log/node.log
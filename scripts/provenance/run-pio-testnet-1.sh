#!/bin/bash

# May need to run `sudo su` to assume root permissions to run `cosmovisor`

export GO_PATH="/home/boba/.go"
export PIO_TESTNET_HOME="/var/provenance/pio-testnet-1"
export DAEMON_NAME="provenanced"
export DAEMON_HOME="${PIO_HOME}"
export DAEMON_ALLOW_DOWNLOAD_BINARIES="true"
export DAEMON_RESTART_AFTER_UPGRADE="true"

script -a -c '$GO_PATH/bin/cosmovisor start --testnet \
--home $PIO_TESTNET_HOME \
--p2p.seeds 2de841ce706e9b8cdff9af4f137e52a4de0a85b2@104.196.26.176:26656,add1d50d00c8ff79a6f7b9873cc0d9d20622614e@34.71.242.51:26656 \
--x-crisis-skip-assert-invariants \
--minimum-gas-prices 1905nhash \
--log_format json' \
-f /var/provenance/pio-testnet-1/log/node.log
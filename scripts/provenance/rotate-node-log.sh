#!/bin/bash

# Get option flags
while getopts f:p: flag
do
    case "${flag}" in
        f) filename_prefix=${OPTARG};;
        p) log_path=${OPTARG};;
        *)
          echo "Incorrect options provided";
          exit 1;;
    esac
done

echo "$filename_prefix";
echo "$log_path";


# Ensure the path of the log file to rotate is set
if [ -z "$log_path" ]
then
 echo "log_path (-p) is not set";
 exit 1;
fi;

# Ensure the filename prefix to rotate to is set
if [ -z "$filename_prefix" ]
then
  filename_prefix="node";
fi

# Get timestamp
datetime=`date +'%Y-%m-%d.%H:%M:%S'`;

echo "Rotating $log_path/node.log to $log_path/${filename_prefix}_$datetime.log";

# Copy active log flile to timestamped log file
cp "$log_path/node.log" "$log_path/${filename_prefix}_$datetime.log";

# Truncate current log
: > "$log_path/node.log"
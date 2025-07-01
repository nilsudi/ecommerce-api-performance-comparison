#!/bin/bash
LOG="cpu_mem.log"
INTERVAL=1

while true; do
  ts=$(date +"%H:%M:%S")

  APP_PID=$(pgrep -f "EcommerceApiApplication")
  PG_PID=$(pgrep -xo postgres)
  MG_PID=$(pgrep -f -n "mongod --dbpath=/Users/nilsudincol/data/db")

  if [ -n "$APP_PID" ];  then ps -p "$APP_PID" -o %cpu=,%mem=,rss= | awk -v t="$ts" '{printf "%s,APP,%s\n",t,$0}'; fi
  if [ -n "$PG_PID" ];   then ps -p "$PG_PID"  -o %cpu=,%mem=,rss= | awk -v t="$ts" '{printf "%s,PG,%s\n", t,$0}'; fi
  if [ -n "$MG_PID" ];   then ps -p "$MG_PID"  -o %cpu=,%mem=,rss= | awk -v t="$ts" '{printf "%s,MONGO,%s\n",t,$0}'; fi

  sleep "$INTERVAL"
done >> "$LOG"

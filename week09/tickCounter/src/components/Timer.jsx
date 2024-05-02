import './Timer.css'
import React, { useState, useEffect } from "react";
function Timer() {
  const [isOngoing, setIsOngoing] = useState(false);
  const [isPaused, setIsPaused] = useState(true);
  const [time, setTime] = useState(0);
  const [minutes, setMinutes] = useState(0);
  const [seconds, setSeconds] = useState(0);

  useEffect(() => {
    let timer;
    if (!isPaused && time > 0) {
      timer = setInterval(() => {
        setTime(time => time - 1);
      }, 1000);
    } else {
      clearInterval(timer);
    }
    return () => clearInterval(timer);
  }, [isPaused, time]);

  function startTimer() {
    setIsPaused(false);
    if (!isOngoing) {
      setTime(minutes * 60 + seconds);
      setIsOngoing(true);
    }
  }

  function pauseTimer() {
    setIsPaused(true);
  }

  function resetTimer() {
    setIsOngoing(false);
    setIsPaused(true);
    setSeconds(0);
    setMinutes(0);
    setTime(0);
  }

  function formatTime(time) {
    const displayMinutes = Math.floor(time / 60)
      .toString()
      .padStart(2, "0");
    const displaySeconds = (time % 60).toString().padStart(2, "0");
    return `${displayMinutes}:${displaySeconds}`;
  }

  return (
    <div>
      <div>
        <div id="presets">
          <button onClick={() => setMinutes(5)}>5 minutes</button>
          <button onClick={() => setMinutes(10)}>10 minutes</button>
          <button onClick={() => setMinutes(15)}>15 minutes</button> <br></br>
        </div>
        <input
          type="number"
          placeholder="Minutes"
          value={minutes===0 ? '' : minutes}
          onChange={(e) => setMinutes(parseInt(e.target.value))}
        />
        <input
          type="number"
          placeholder="Seconds"
          value={seconds===0 ? '' : seconds}
          onChange={(e) => setSeconds(parseInt(e.target.value))}
        />
        <div id="start_stop">
          <button onClick={(isPaused) ? startTimer : pauseTimer}>{isPaused ? "Start" : "Paused"}</button>
          <button onClick={resetTimer}>Reset</button>
        </div>
      </div>
      <div id="time" >Time Remaining <br></br> {formatTime(time)}</div>
    </div>
  );
}


export default Timer;
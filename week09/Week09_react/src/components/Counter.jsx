import { useEffect, useState } from 'react';

function Counter({initial, increment}) {
  const [count, setCount] = useState(initial)
  function handleClick() {
    setCount(count + increment)
    localStorage.setItem("count", count+1)
  }

  return (
    <>
      <div>
        <button onClick={handleClick} type="button">Increment</button>
        <p>{count}</p>
      </div>
    </>
  )
}

export default Counter;
import './App.css'
import { useEffect } from 'react';
import Counter from './components/Counter'
import ListDemo from './components/ListDemo';

function App() {
  let initial = 0;

  return (
    <>
    <h1>Counter</h1>
    <Counter initial={parseInt(localStorage.getItem("count"))} increment={1}/>
    <hr></hr>
    <ListDemo />
    </>
  )
}

export default App;
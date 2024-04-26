import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import upper, { text1, text2, text3 } from "./file";
import person,  {males, females} from "./file2";
import {Welcome, MultiWelcome} from "./file3"
import GetJoke from './components/ChuckNorris.jsx';
import GetRandomJoke from './components/RandomJoke.jsx';

function App() {
  const {firstName, email, gender, lastName, phone} = person;
  console.log(...females, ...males);

  const personV2 = {
    email,
    firstName,
    frinds: [...males, ...females],
    gender,
    lastName,
    phone: 1234545
  }

  console.log(personV2);
  return(
  <>
    <h2>Ex 1</h2>
    <p>{text1}</p>
    <p>{text2}</p>
    <p>{text3}</p>
    <p>{upper("upper case this")}</p>
    <h2>Ex 2</h2>
    <p>{firstName}, {email} </p>
    <h2>Ex 3</h2>
    <MultiWelcome />
    <h1>Ex 4, Chuck Norris Joke:</h1>
    <GetJoke /> 
     <h1>Get Random Dad Joke</h1>
    <GetRandomJoke />
  </>
  );
}

export default App

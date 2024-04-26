import React from "react";
import {persons} from "./file2";

function Welcome(props) {
  return <h1>Hello, {props.name}</h1>;
}

function MultiWelcome() {
  return (
    <div>
      <Welcome name="Sara" />
      <Welcome name="Cahal" />
      <Welcome name="Edith" />
      <WelcomePerson person={persons[0]}  />
      <WelcomePerson person={persons[1]}  />
      <WelcomePerson person={persons[2]}  />
      {persons.map((person)=><WelcomePerson person={person} />)}
    </div>
  );
}

function WelcomePerson({person}) {
  const {firstName, lastName, email} = person;
  return (
    <>
      <p>{firstName} {lastName}, email: {email}</p>
    </>
  )
}

export { Welcome, MultiWelcome };
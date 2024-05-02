import { useEffect, useState } from "react";
import "./styles/App.css";
import PersonList from "./components/PersonList";
import PersonForm from "./components/PersonForm";
import { fetchData } from "./util/persistance";

function App() {
  const blankPerson = { id: "", name: "", age: "", email: "", gender: "" };
  const [persons, setPersons] = useState([]);
  const [personToEdit, setPersonToEdit] = useState(blankPerson);
  const APIURL = "http://localhost:3000/api";

  function fetchPersons(callBack) {
    fetchData(APIURL, callBack);
  }

  function deletePerson(id) {
    fetchData(`${APIURL}/${id}`, () => {}, "DELETE");
    setPersons(persons.filter((person) => person.id !== id));
  }

  function editPerson(person) {
    setPersonToEdit(person);
  }
  
  function createPerson(person) {
    person.id = crypto.randomUUID();
    fetchData(
      APIURL,
      (person) => {
        setPersons([...persons, person]);
      },
      "POST",
      person
    );
  }

  function updatePerson(person) {
    fetchData(
      `${APIURL}/${person.id}`,
      (person) => {
        setPersons(
          persons.map((p) => (p.id === person.id ? { ...person } : p))
        );
      },
      "PUT",
      person
    );
    console.log(person);
  }

  function mutatePerson(person) {
    person.id ? updatePerson(person): createPerson(person);
  }

  useEffect(() => {
    fetchPersons((data) => setPersons(data));
  }, []);

  return (
    <>
      <div className="main">
        <PersonList
          persons={persons}
          deletePerson={deletePerson}
          editPerson={editPerson}
        />
        <div className="personForm">
          <PersonForm
            blankPerson={blankPerson}
            mutatePerson={mutatePerson}
            personToEdit={personToEdit}
          />
        </div>
      </div>
    </>
  );
}

export default App;

import React, { useState, useEffect } from "react";

export default function PersonForm({
  blankPerson,
  mutatePerson,
  personToEdit,
}) {
  const [person, setPerson] = useState({ ...personToEdit });
  const handleChange = (e) => {
    setPerson({ ...person, [e.target.id]: e.target.value });
  };

  useEffect(() => {
    setPerson(personToEdit);
  }, [personToEdit]);

  return (
    <form>
      <label htmlFor="id">Id</label>
      <input
        id="id"
        type="number"
        placeholder="id"
        value={person.id}
        onChange={handleChange}
      />
      <br></br>
      <label htmlFor="name">Name</label>
      <input
        id="name"
        type="text"
        placeholder="name"
        value={person.name}
        onChange={handleChange}
      />
      <br></br>
      <label htmlFor="age">Age</label>
      <input
        id="age"
        type="number"
        min="1"
        max="120"
        placeholder="age"
        value={person.age}
        onChange={handleChange}
      />{" "}
      <br></br>
      <label htmlFor="email">Email</label>
      <input
        id="email"
        type="email"
        placeholder="email"
        value={person.email}
        onChange={handleChange}
      />
      <br></br>
      <label htmlFor="gender">Gender</label>
      <select id="gender" value={person.gender} onChange={handleChange}>
        <option defaultChecked>Select Gender</option>
        <option value="male">Male</option>
        <option value="female">Female</option>
        <option value="other">Other</option>
      </select>
      <button
        onClick={(e) => {
          e.preventDefault();
          mutatePerson(person);
        }}
      >
        Create
      </button>
      <button onClick={() => setPerson(blankPerson)}>Reset</button>
    </form>
  );
}

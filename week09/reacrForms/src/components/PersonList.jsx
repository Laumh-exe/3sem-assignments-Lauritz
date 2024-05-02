import React, { useState, useEffect } from "react";

export default function PersonList({ persons, deletePerson, editPerson }) {
  return (
    <table className="table table-striped">
      <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
          <th>Age</th>
          <th>Email</th>
          <th>Gender</th>
          <th></th>
        </tr>
      </thead>
      {persons.map((person) => (
        <tbody key={person.id}>
          <tr>
            <td>{person.id}</td>
            <td>{person.name}</td>
            <td>{person.age}</td>
            <td>{person.email}</td>
            <td>{person.gender}</td>
            <td>
              <button onClick={() => editPerson(person)}>Edit</button>
              <button onClick={() => deletePerson(person.id)}>Delete</button>
            </td>
          </tr>
        </tbody>
      ))}
    </table>
  );
}

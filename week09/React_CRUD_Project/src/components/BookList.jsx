import React, { useState, useEffect } from 'react'
import './bookList.css'

export default ({updated, setUpdated}) => {
  const [books, setBooks] = useState([]);

  const handleDelete = (e) => {

    e.preventDefault();
    const id = e.target.id;
    console.log(id);
    fetch(`http://localhost:3001/books/${id}`, {
      method: "DELETE",
    }).then(setUpdated(!updated));
  }

  useEffect(() => {
    if(updated) setUpdated(!updated);
    fetch("http://localhost:3001/books")
      .then((res) => res.json())
      .then((data) => setBooks(data));
  }, [updated]);
  return (
    <div><h1>BookList:</h1>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Title</th>
          <th>Author</th>
          <th>Year Published</th>
          <th>Rating</th>
          <th></th>
        </tr>
      </thead>
      {books.map((book) =>
        <tbody key={book.id}>
          <tr>
            <td>{book.id}</td>
            <td>{book.title}</td>
            <td>{book.author}</td>
            <td>{book.year_published}</td>
            <td>{book.year_published}</td>
            <td><button id={book.id} onClick={handleDelete}>Delete</button></td>
          </tr>
        </tbody>
      )}
      </table>
    </div>
  )
}

import React, { useState, useEffect } from 'react'

export default ({ updated, setUpdated }) => {
  const [book, setBook] = useState({})
  const [edit, setEdit] = useState(false);
  const [error, setError] = useState('')

  const handleChange = (e) => {
    if (e.target.id === 'id') {
      setEdit(e.target.value !== '');
    }
    console.log(e.target.value);
    setBook({ ...book, [e.target.id]: e.target.value });
  }

  const handleSubmit = (e) => {
    setError('');
    e.preventDefault();
    fetch('http://localhost:3001/books', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(book),
    }).then((response) => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    }).then((data) => {
      setUpdated(!updated);
      setEdit(false);
      e.target.reset();
    }).catch((err) => setError('Something went wrong'));
  }

  const handleEdit = (e) => {
    setError('');
    e.preventDefault();
    fetch('http://localhost:3001/books/' + book.id, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(book),
    }).then((response) => {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    }).then((data) => {
      setUpdated(!updated);
      setEdit(false);
      e.target.reset();
    }).catch((err) => {
      console.log("Error: ", err)
      setError('Book not found')
    });
  }

  return (
    <>
      <form onSubmit={edit ? handleEdit : handleSubmit}>
        <input id="title" type="text" placeholder="Enter Title" onChange={handleChange} />
        <input id="author" type="text" placeholder="Enter Author" onChange={handleChange} />
        <input id="year_published" type="text" placeholder="Enter Year Published" onChange={handleChange} />
        <input id="rating" type="text" placeholder="Enter Rating" onChange={handleChange} />
        <input id="id" type="text" placeholder="Enter ID" onChange={handleChange} />
        <button type='submit'>{edit ? 'Edit book' : 'Add Book'}</button>
        <h3>{error}</h3>
      </form>
    </>
  )
}

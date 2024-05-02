import React, { useEffect,useState } from "react";
import BookList from "./BookList";
import BookForm from "./BookForm";

export default () => {
  const [updated, setUpdated] = useState(false);

  return (
    <div>
      <BookForm updated={updated} setUpdated={setUpdated}/>
      <BookList updated={updated} setUpdated={setUpdated}/>
    </div>
  );
}
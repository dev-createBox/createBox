import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Posts.css';

function Posts() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios
      .get('https://jsonplaceholder.typicode.com/posts')
      .then((res) => {
        setPosts(res.data);
      })
      .catch((err) => {
        console.log(err);
      });
  });

  return (
    <div className='post_body'>
      {posts.map((post) => (
        <div className='post_wrap' key={post.id}>
          <div className='post_id'>이름({post.id})</div>
          <div className='post_timestamp'>Timestamp...</div>
          <div className='post_title'>{post.title}</div>
        </div>
      ))}
    </div>
  );
}

export default Posts;
//  {posts.map((post) => (
//  <li key={post.id}>{post.title}</li>
//  )}

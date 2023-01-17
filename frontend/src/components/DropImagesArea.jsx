import styled from "styled-components";

const Container = styled.div`
  width: 100%;
  height: 250px;
  border: 2px dashed #ccc;
  display: flex;
  align-items: center;
  justify-content: center;
`;

function DropImagesArea({ onDrop, onStartResizing }) {
  const handleDragEnter = (e) => {
    e.preventDefault();
    e.stopPropagation();
    console.info("drag enter");
  };
  const handleDragLeave = (e) => {
    e.preventDefault();
    e.stopPropagation();
    console.info("drag leave");
  };
  const handleDragOver = (e) => {
    e.preventDefault();
    e.stopPropagation();
  };
  const handleDrop = (e) => {
    e.preventDefault();
    e.stopPropagation();
    if (e.dataTransfer.files && e.dataTransfer.files.length > 0) {
      onStartResizing();
      onDrop(Array.from(e.dataTransfer.files));
      e.dataTransfer.clearData();
    }
  };

  return (
    <Container
      onDragEnter={handleDragEnter}
      onDragLeave={handleDragLeave}
      onDragOver={handleDragOver}
      onDrop={handleDrop}
    >
      Drop files here...
    </Container>
  );
}

export default DropImagesArea;

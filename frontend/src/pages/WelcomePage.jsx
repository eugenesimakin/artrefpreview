import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import { Container } from "../components/styled";

const Block = styled.div`
  display: flex;
  flex-direction: column;
  gap: 20px;
`;

const Header = styled.h2`
  text-align: center;
`;
const StartBtn = styled.button`
  width: 150px;
  margin: 0 auto;
`;

function WelcomePage() {
  const navigate = useNavigate();

  const newCollection = (evt) => {
    const id = Math.random().toString(36).substring(2, 15);
    navigate(`/c/${id}`);
  };

  return (
    <Container>
      <Block>
        <Header>
          Organize your reference pictures into collections for quick preview
        </Header>
        <StartBtn
          className="bttn-minimal bttn-md bttn-primary"
          onClick={newCollection}
        >
          Start
        </StartBtn>
      </Block>
    </Container>
  );
}

export default WelcomePage;

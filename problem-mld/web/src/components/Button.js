import styled from 'styled-components';
import {withProps} from 'recompose';


const Button = styled.button`
  cursor: pointer;
  background: ${props => props.theme.color.accent};
  color: white;
  border: 0;
  border-radius: 2px;
  padding: .4rem .8rem;
  letter-spacing: .1rem;

  &[disabled] {
    cursor: not-allowed;
    background: grey;
  }
`;

const enhance = withProps({type: 'button'});

export default enhance(Button);
